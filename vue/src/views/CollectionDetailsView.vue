<template>
  <div class="collections-details">
    <h1 class="collection-name">{{ collection.collectionName }}</h1>
    <div class="collection-controls" v-if="this.$route.params.id==this.$store.state.user.id">
      <button @click="setPublic">Publish Collection</button>
      <button @click="setPrivate">Unpublish Collection</button>
    </div>
    <div class="searchBox">
      <div class="field">
        <input
          type="text"
          id="searchTerm"
          name="searchTerm"
          @keyup="searchCollection()"
        />
        <button @click="searchCollection()">
          Search<img src="/images/SearchIconBlack.png" class="search-icon" />
        </button>
      </div>
    </div>
    <div class="card-container" v-if="this.displayedCards.length > 0">
      <div
        class="card"
        v-for="card in this.displayedCards"
        v-bind:key="card.cardId"
      >
        <router-link
          class="router-link"
          v-bind:to="{ name: 'cardDetails', params: { id: card.cardId } }"
        >
          <img :src="card.imageUrl" />
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService";

export default {
  data() {
    return {
      displayedCards: [],
      collection: {
        cardCount: 0,
        collectionName: "",
        ownerId: 0,
        thumbnailUrl: "",
        username: "",
        cards: [],
      },
    };
  },

  methods: {
    setPrivate() {
      CollectionService.setCollectionPrivate(this.$route.params.id)
      .then((response) => {
        alert('This collection is now unpublished')
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
    },
    setPublic() {
      CollectionService.setCollectionPublic(this.$route.params.id)
      .then((response) => {
        alert('This collection has been published')
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
    },
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
    getDeckById() {
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
          console.log(response.data);
          this.collection.cards = response.data;
          this.displayedCards = this.collection.cards;
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    searchCollection() {
      let search = document.getElementById('searchTerm').value;
      let tempArray = 
      this.collection.cards.filter((card) => {
        return card.cardName.toLowerCase().includes(search.toLowerCase()) ||
        card.cardType.toLowerCase().includes(search.toLowerCase()) ||
        card.rarity.toLowerCase().includes(search.toLowerCase()) ||
        card.setName.toLowerCase().includes(search.toLowerCase());
      });
      this.displayedCards = tempArray;
    }
  },
  created() {
    this.getCollectionById();
  },
};
</script>

<style scoped>
.collection-controls {
  flex: display;
  align-content: center;
  width: 25%;
  background-color: red;
}

.collection-name {
  color: var(--onyx);
}
.advanced-card-container {
  width: 80%;
  height: 800px;
  background-color: var(--onyx);
  margin: 50px auto;
  position: relative;
}

</style>