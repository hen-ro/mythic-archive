import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {

    search(searchTerm) {
        return axios.get(`https://api.scryfall.com/cards/search?q=${searchTerm}*&order=name`)
    },


}