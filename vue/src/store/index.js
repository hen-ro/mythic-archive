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
      collectionCards: new Map()
    },
    mutations: {
      SET_CARDS_IN_COLLECTION(state, cards){
        state.collectionCards = cards;
      },
      ADD_CARD_TO_COLLECTION(state, payload){
        const { card, quantity } = payload;
        state.collectionCards.set(card, quantity);
      },
      REMOVE_CARD_FROM_COLLECTION(state, payload){
        const { card, quantity } = payload;
        const currentQuantity = state.collectionCards.get(card);
        if (currentQuantity <= quantity ) {

        } else {

        }
        
      },
      BUILD_REQUEST(state, payload) {
        const { card, quantity } = payload;
        state.request.userId = state.user.id;
        state.request.quantity = quantity;
        //Set card properties
        state.request.card.cardId = card.id;
        state.request.card.cardName = card.name;
        state.request.card.cardType = card.type_line;
        state.request.card.manaCost = card.mana_cost;
        state.request.card.rarity = card.rarity;
        state.request.card.price = card.prices.usd;
        state.request.card.setName = card.set_name;
        state.request.card.thumbnailUrl = card.image_uris ? card.image_uris?.art_crop : card.card_faces ? card.card_faces[0].image_uris?.art_crop : '';
        state.request.card.imageUrl = card.image_uris ? card.image_uris?.large : card.card_faces ? card.card_faces[0].image_uris?.large : '';
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
        console.log(this.state.request)
        CollectionService.addCardToCollection(this.state.request)
          .then((response) => {
            alert('This card has been added to your collection')
          })
          .catch((error) => {
            console.error("Error fetching data:", error);
          });
      },
      removeFromCollection({commit}, payload) {
        this.commit('BUILD_REQUEST', payload);
        CollectionService.removeCardFromCollection(this.state.request)
          .then((response) => {
            alert('This card has been removed from your collection')
          })
          .catch((error) => {
            console.error("Error fetching data:", error);
          });
      },
      //These are adding/removing cards from deck
      setCardsInDeck({commit}, cards) {
        this.commit('SET_CARDS', cards);
      },
      addCardToDeck({commit}, card) {
        this.commit('ADD_CARD', card);
      },
      removeCardFromDeck({commit}, card) {
        this.commit('REMOVE_CARD', card);
      },
      //This is adding/removing cards from collection
      setCardsInCollection({commit}, cards) {
        this.commit('SET_CARDS', cards);
      },
      
    },

  });
  return store;
}
