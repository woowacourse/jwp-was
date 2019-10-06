package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContentTypeFactory {
    private static final Logger log = LoggerFactory.getLogger(ContentTypeFactory.class);

    private final static String ACCEPT_SPLITTER = ",";
    private final static String ACCEPT_ELEMENT_SPLITTER = ";";

    public static ContentType create(String accept, ContentTypesSupplier contentTypesSupplierSupportedByServer) {
        List<ContentType> expectedContentTypesFromClient = supplyContentTypesFromClient(accept);
        log.debug("contentTypesFromClient: {}", Arrays.toString(expectedContentTypesFromClient.toArray()));

        List<ContentType> contentTypesSupportedByServer = contentTypesSupplierSupportedByServer.get();
        log.debug("contentTypesFromServer: {}", Arrays.toString(contentTypesSupportedByServer.toArray()));

        return matchFrom(expectedContentTypesFromClient, contentTypesSupportedByServer)
                .orElseThrow(() -> NotAcceptableException.from(accept));
    }

    // [TODO] 부가기능(';' 이후에 오는 정보들) 고려하기
    private static List<ContentType> supplyContentTypesFromClient(String accept) {
        String acceptWithoutSpace = accept.replace(" ", "");
        log.debug("acceptWithoutSpace :{}", acceptWithoutSpace);

        return Arrays.asList(acceptWithoutSpace.split(ACCEPT_SPLITTER)).stream()
                .map(element -> element.split(ACCEPT_ELEMENT_SPLITTER)[0])
                .filter(ContentType::isSupportedMimeType)
                .map(mimeType -> ContentType.fromMimeType(mimeType).get())
                .collect(Collectors.toList());
    }

    private static Optional<ContentType> matchFrom(List<ContentType> expectedTypes, List<ContentType> supportedTypes) {
        for (ContentType expectedType : expectedTypes) {
            for (ContentType supportedType : supportedTypes) {
                if (expectedType.canAccept(supportedType)) {
                    return Optional.of(supportedType);
                }
            }
        }
        return Optional.empty();
    }

    public static boolean canCreate(String accept, ContentType wantedContentType) {
        return supplyContentTypesFromClient(accept).stream()
                .anyMatch(contentType -> contentType.canAccept(wantedContentType));
    }
}
