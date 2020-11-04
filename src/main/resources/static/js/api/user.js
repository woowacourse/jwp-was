export const fetchList = () => {
    return axios.get("/user/list", {
        headers: {
            "Content-Type": "application/json"
        }
    })
        .catch(error => location.replace("/user/login.html"))
}
