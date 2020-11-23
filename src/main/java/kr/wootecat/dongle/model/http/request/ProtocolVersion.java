package kr.wootecat.dongle.model.http.request;

import static java.lang.String.*;

import java.util.Arrays;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

public enum ProtocolVersion {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1");

    private static final String UNSUPPORTED_VERSION_MESSAGE_FORMAT = "서버에서 지원하는 protocol 버전이 아닙니다: %s";

    private final String version;

    ProtocolVersion(String version) {
        this.version = version;
    }

    public static ProtocolVersion of(String version) {
        return Arrays.stream(values())
                .filter(protocolVersion -> protocolVersion.version.equalsIgnoreCase(version))
                .findAny()
                .orElseThrow(() -> new IllegalRequestDataFormatException(
                        format(UNSUPPORTED_VERSION_MESSAGE_FORMAT, version)));
    }

    public String getVersion() {
        return version;
    }
}
