import {fetchList} from "./api/user.js"
import {userTemplate} from "./templates.js";

function User_list() {
    const $table = document.querySelector(".table")
    const list = async () => await fetchList()

    this.init = () => {
        list()
            .then(res => {
                res.data.forEach((item, index) => {
                    const newRow = $table.insertRow($table.rows.length)
                    newRow.innerHTML = userTemplate(item, index)
                })
            })
    }
}

const list = new User_list()
list.init()
