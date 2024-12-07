<template>
  <div class="collections-details">
    <h1>{{ collectionName }}</h1>
    <div class="searchBox">
      <div class="field">
        <input type="text" id="searchTerm" name="searchTerm" v-model="this.searchTerm" />
        <button @click="search">Search<img src='/images/SearchIconBlack.png' class="search-icon"></button>
      </div>
    </div>
    <div class="card-container" v-if="cards.length > 0">
      <div class="card" v-for="card in this.cards" v-bind:key="card.id" v-bind:card="card">
        <router-link class="router-link" v-bind:to="{ name: 'cardDetails', params: { id: card.id } }">        
        <img :src="card.imageUrl" class="add-card" />
      </router-link>
      </div>
    </div>
  </div>
</template>
  
  <script>
export default {
  data() {
    return {
      collectionName: "",
    };
  },
  mounted() {
    this.collectionName = this.$route.params.name;
    console.log(this.collectionName);
  },

  methods: {},
};
</script>
  
  <style scoped>
h1 {
  color: #1e7a76;
}
</style>
