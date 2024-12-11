<template>
  <div class="collections-details">
    <h1 class="collection-name">{{ collection.collectionName }}</h1>
    <div class="collection-controls" v-if="this.$route.params.id==this.$store.state.user.id">
      <button id="btn-show-name-input" @click="showNameInput = true">Rename Collection</button>
      <button id="btn-publish" @click="setPublic">Publish Collection</button>
      <button id="btn-unpublish" @click="setPrivate">Unpublish Collection</button>
    </div>
    <div class="collection-name-input" v-if="showNameInput === true">
      <button id="btn-close-name-input" @click="showNameInput = false">X</button>
      <div class="name-input">
        <input type="text" id="txt-nameInput" placeholder="Enter new collection name">
        <button id="btn-name-submit" @click="rename">Submit</button>
      </div>
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
      showNameInput: false,
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
    rename() {
      let collectionName = document.getElementById('txt-nameInput').value;
      CollectionService.renameCollection(this.$route.params.id, collectionName)
      .then((response) => {
        this.collection.collectionName = collectionName;
        //SET_NOTIFICATION to 'Your collection has been renamed'
        this.showNameInput = false;
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
    },
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
  display: flex;
  justify-content: space-evenly;
}
.collection-name-input {
  position: fixed;
  top: 50%;
  left: 50%; 
  transform: translate(-50%, -50%);
  width: 30%;
  padding: 30px;
  background-color: var(--platinum);
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  z-index: 1000;
}
.name-input {
  display: flex;
  align-content: center;
  justify-content: space-around;
}

#btn-close-name-input { 
  position: absolute; 
  top: 10px; 
  right: 10px;
  background-color: var(--platinum);
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