<template>
  <div class="collections-details">
    <h1>{{ collectionName }}</h1>
    <div class="searchBox">
      <div class="field">
        <input type="text" id="searchTerm" name="searchTerm" v-model="this.searchTerm" />
        <button @click="search">
          Search<img src="/images/SearchIconBlack.png" class="search-icon" />
        </button>
      </div>
    </div>
    <div class="card-container">
      <div class="card" v-for="card in this.collection.cards" v-bind:key="card.id" v-bind:card="card">
        <router-link class="router-link" v-bind:to="{ name: 'collectionDetails', params: { id: card.id } }">
          <img :src="card.imageUrl" />
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import CollectionService from '../services/CollectionService';

export default {
  data() {
    return {
      collectionName: "",
      collection: {
        cardCount: 0,
        collectionName: "",
        ownerId: 0,
        thumbnailUrl: "",
        username: "",
        cards: []
      },
    };
  },

  methods: {
    getCollectionById() {
      CollectionService.getCollectionById(this.$route.params.id)
        .then((response) => {
          this.collection = response.data;
          
          this.getCardsInCollection();
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    getCardsInCollection() {
      CollectionService.getCardsInCollection(this.$route.params.id)
        .then((response) => {
          this.collection.cards = response.data
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },

    

  },created() {
      this.getCollectionById();
      console.log('test');

    },
};

</script>

<style scoped>
h1 {
  color: #1e7a76;
}

.card-container {
  width: 80%;
  height: 800px;
}

.card {
  width: 25%;
  height: 100px;
  background-color: red;
}
</style>
