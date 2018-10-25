import axios from 'axios'

const TIMEOUT_MS = 6000

export const get = (url) => {
    return axios({
        method: 'get',
        url: url,
        timeout: TIMEOUT_MS
    }).then(response => {
            return response;
        })
    .catch(error =>
        console.log(error.response)
    )
}

