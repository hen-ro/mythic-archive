<template>
  <div class="home">
    <h1>Home</h1>
    <button @click="search">Run Method</button>
    <div class="field">
      <label for="search">Search cards</label>
      <textarea id="searchTerm" name="searchTerm" v-model="this.searchTerm" />
    </div>
    <div class="card-container" v-if="cards.length > 0">
      <router-link v-bind:to="{ name: 'card' }">
      <div  class="card" v-for="card in this.cards" v-bind:key="card.id" v-bind:card="card">
        <img class="card-img" :src="card.imageUrl" />
        <p class="card-name"> {{ card.name }} </p>
      </div>
    </router-link>
    </div>
  </div>
</template>

<script>
import SearchService from "../services/SearchService"

export default {

  data() {
    return {
      searchTerm: '',
      cards: []
    }
  },

  methods: {
    // Basic Axios GET request to fetch data from Scryfall API
    search() {
      SearchService.search(this.searchTerm).then(response => {
        this.cards = response.data.data.map((card) => ({
          id: card.id,
          name: card.name,
          imageUrl: card.cards_faces ? card.cards_faces[0].image_uris?.normal : card.image_uris?.normal || " ",
        }));
        console.log(this.cards);  // Print the full response data
      })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    }
  }
}
</script>

<style scoped>

  .card-container{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
    align-items: center;
    row-gap: 20px;
    background-color: red;
  }

  .card {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 23%;
    background-color: green;
  }

  .card-name {
    width: 80%;
    height: 20px;
    margin: 5px auto;
    text-align: center;
    font-size: 1.8vw;
    background-color: blue;
  }

  .card-img {
    box-shadow: 0px 0px 17px 0px rgba(0,0,0,0.44);
    border-radius: 10px;
  }

  router-link {
    text-decoration: none;
    display: inline;
    background-color: purple;
  }


</style>