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
    <div class="card-container" v-if="cards.length > 0">
      <div class="card" v-for="card in this.cards" v-bind:key="card.id" v-bind:card="card">
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
          this.collections = response.data.map((collection) => ({
            cardCount: collection.cardCount,
            collectionName: collection.collectionName,
            ownerId: collection.ownerId,
            thumbnailUrl: collection.thumbnailUrl,
            username: collection.username
          }));
          this.getCardsInCollection();
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    getCardsInCollection() {
      CollectionService.getCardsInCollection(this.$route.params.id)
        .then((response) => {
          this.collections.cards = response.data
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },

    mounted() {
      this.collectionName = this.$route.params.name;
      console.log(this.collectionName);
    },

  }
};

</script>

<style scoped>
h1 {
  color: #1e7a76;
}
</style>
