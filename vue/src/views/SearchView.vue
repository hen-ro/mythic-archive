<template>
  <div class="home">
    <h1>Search</h1>
    <div class="searchBox">
      <div class="field">
        <input type="text" id="searchTerm" name="searchTerm" v-model="this.searchTerm" />
        <button @click="search">Search<img src='/images/SearchIconBlack.png' class="search-icon"></button>
      </div>
    </div>
    <div class="card-container" v-if="cards.length > 0">
      <div class="card" v-for="card in this.cards" v-bind:key="card.id" v-bind:card="card">
        <router-link class="router-link" v-bind:to="{ name: 'cardDetails', params: { id: card.id } }">        
        <img :src="card.imageUrl" />
      </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import SearchService from "../services/SearchService";

export default {
  data() {
    return {
      searchTerm: "",
      cards: [],
    };
  },

  methods: {
    search() {
      SearchService.search(this.searchTerm)
        .then((response) => {
          this.cards = response.data.data.map((card) => ({
            id: card.id,
            name: card.name,
            imageUrl: card.image_uris
              ? card.image_uris?.normal || ""
              : card.card_faces ? card.card_faces[0].image_uris?.normal || "" : ""
          }));
          console.log(this.cards);
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
  }, mounted() {
    this.search();
  }
};
</script>

<style scoped>
.router-link {
  text-decoration: none;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: center;
  row-gap: 20px;
  width: 80%;
  margin: 50px auto;
}

.card {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 18%;
  margin: 10px;
  text-decoration: none;
  transition: transform 0.1s ease-in-out;

}

.card p {
  width: 80%;
  height: 20px;
  margin: 5px auto;
  text-align: center;
  font-size: 1.2vw;

}

.card img {
  border-radius: 13px;
  width: 100%;
  transition: transform 0.1s ease-in-out;
  transition: box-shadow 0.5s ease;

}


.card img:hover {
  transform: scale(1.02);
  transition: transform 0.1s ease-in-out;
  border-radius: 13px;
  box-shadow: 0px 0px 20px 10px #0e0e0e;
  transition: box-shadow 0.3s ease;
  cursor: pointer;
}

.field {
  margin: 40px auto;
  width: 30%;
  display: flex;
  justify-content: space-evenly;
}

input {
  width: 80%;
  height: 30px;
  border-radius: 2px;
  border: 0px;
  background-color: var(--platinum);
  border: 1px solid var(--keppel);
}

button {
  border: 0px;
  border-radius: 2px;
  background-color: var(--keppel);
  display: flex;
  align-items: center;
}

button:hover {
  cursor: pointer;
}

button:active {
  background-color: var(--perry);
}

.search-icon {
  width: 13px;
  margin-left: 5px;
}</style>
