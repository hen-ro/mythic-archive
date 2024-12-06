import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {

    getAllPublicCollections() {
        return http.get('http://localhost:9000/collections/all-public');
    },

    getCollectionByName(name) {
        return http.get(`http://localhost:9000/collections/${name}`);
    }

}