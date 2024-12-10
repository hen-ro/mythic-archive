import { createStore as _createStore } from 'vuex';
import axios from 'axios';
import CollectionService from '../services/CollectionService';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      collectionId: 0,
      request: {
        card: {
          cardId: '',
          cardName: '',
          thumbnailUrl: '',
          imageUrl: ''
        },
        userId: 0,
        quantity: 0
      },
      cards: []
    },
    mutations: {
      BUILD_REQUEST(state, payload) {
        const { card, quantity } = payload;
        state.request.userId = state.user.id;
        state.request.card.cardId = card.id;
        state.request.card.cardName = card.name;
        state.request.thumbnailUrl = card.image_uris ? card.image_uris?.art_crop : card.card_faces ? card.card_faces[0].image_uris?.art_crop : '';
        state.request.imageUrl = card.image_uris ? card.image_uris?.large : card.card_faces ? card.card_faces[0].image_uris?.large : '';
        state.request.quantity = quantity;
      },
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      SET_COLLECTION_ID(state, collectionId) {
        state.collectionId = collectionId;
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      }
    },
    actions: {
      getCollectionIdByUser() {
        CollectionService.getCollectionIdByUser(this.state.user.id)
          .then((response) => {
            let id = response.data;
            this.commit('SET_COLLECTION_ID', id);
          })
          .catch((error) => {
            console.error("Error fetching data:", error);
          });
      },
      addToCollection({commit}, payload) {
        this.commit('BUILD_REQUEST', payload);
        CollectionService.addCardToCollection(this.state.request)
          .then((response) => {
            console.log(response)
          })
          .catch((error) => {
            console.error("Error fetching data:", error);
          });
      },
      removeFromCollection({commit}, payload) {
        this.commit('BUILD_REQUEST', payload);
        CollectionService.removeCardFromCollection(this.state.request)
          .then((response) => {
            console.log(response)
          })
          .catch((error) => {
            console.error("Error fetching data:", error);
          });
      },
    },

  });
  return store;
}
