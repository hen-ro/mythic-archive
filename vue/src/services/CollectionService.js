import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {

    getAllPublicCollections() {
        return http.get('http://localhost:9000/collections/all-public');
    },

    getCollectionById(id) {
        return http.get(`http://localhost:9000/collections/${id}`);
    },

    getCardsInCollection(id) {
        return http.get(`http://localhost:9000/collections/${id}/cards`);
    },

    getCollectionIdByUser(id) {
        return http.get(`http://localhost:9000/collections/user/${id}`);
    },

    addCardToCollection(request) {
        return http.put(`http://localhost:9000/collections/add`, request);
    },

    removeCardFromCollection(request) {
        return http.delete(`http://localhost:9000/collections/remove`, {data : request});
    },

    removeAllFromCollection(request) {
        return http.delete(`http://localhost:9000/collections/remove-all`, {data : request});
    },

    setCollectionPublic(collectionId) {
        return http.put(`http://localhost:9000/collections/${id}/set-public`);
    }

}