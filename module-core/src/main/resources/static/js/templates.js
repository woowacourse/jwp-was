export const userTemplate = (user, index) => {
    return `
<th scope="row">${index + 1}</th>
<td>${user.userId}</td>
<td>${user.name}</td>
<td>${user.email}</td>
<td><a href="#" class="btn btn-success" role="button">수정</a></td>`
}
