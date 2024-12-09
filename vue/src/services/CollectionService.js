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
        return http.get(`http://localhost:9000/collections/by-user/${id}`);
    },

    addCardToCollection(card, userId, quantity) {
        return http.put(`http://localhost:9000/collections/add`);
    },

    removeCardFromCollection(card, quantity) {
        return http.delete(`http://localhost:9000/collections/remove`);
    },

    removeAllFromCollection(card) {
        return http.delete(`http://localhost:9000/collections/remove-all`);
    }

}