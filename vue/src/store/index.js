import { createStore as _createStore } from 'vuex';
import axios from 'axios';
import CollectionService from '../services/CollectionService';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      collectionId: 0
    },
    mutations: {
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
        }
    }
  });
  return store;
}
