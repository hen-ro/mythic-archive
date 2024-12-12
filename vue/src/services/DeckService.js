import axios from 'axios';
const userToken = localStorage.getItem('token')
const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
  headers: {Authorization: `Bearer ${userToken}`}
});

export default {

    getAllPublicCollections() {
        return http.get('http://localhost:9000/decks/all-public');
    }

}