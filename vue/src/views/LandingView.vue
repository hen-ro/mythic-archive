<template>
    <div class="home">
        <h1>Home</h1>
        <button @click="search">Run Method</button>
        <div class="field">
            <label for="search">Search cards</label>
            <textarea id="searchTerm" name="searchTerm" v-model="this.searchTerm"></textarea>
        </div>
        <div class="card-container" v-if="cards.length > 0">
            <div class="card" v-for="card in this.cards" v-bind:key="card.id" v-bind:card="card">
                <router-link class="router-link" v-bind:to="{ name: 'cardDetails' }"><img
                        :src="card.imageUrl" /></router-link>
                <p> {{ card.name }} </p>
            </div>
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
                    imageUrl: card.image_uris?.normal || "",
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
.router-link {
    text-decoration: none;
}

.card-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
    align-items: center;
    row-gap: 20px;
}

.card {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 18%;
    margin: auto 10px;
    text-decoration: none;
}

.card p {
    width: 80%;
    height: 20px;
    margin: 5px auto;
    text-align: center;
    font-size: 1.8vw;
}

.card img {
    border-radius: 10px;
    width: 100%;
}
</style>