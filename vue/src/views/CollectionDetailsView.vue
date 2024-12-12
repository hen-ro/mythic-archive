<template>
  <div class="collections-details">
    <h1 class="collection-name">{{ collection.collectionName }}</h1>
    <div class="collection-controls" v-if="this.$route.params.id == this.$store.state.user.id">
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
        <input type="text" id="searchTerm" name="searchTerm" @keyup="searchCollection()" />
        <button @click="searchCollection()">
          Search<img src="/images/SearchIconBlack.png" class="search-icon" />
        </button>
      </div>
    </div>
    <div class="content-container">
      <div class="left-container">
        <div class="header-stats">
          <div class="stat-item">
            <h3>Rarities</h3>
            <ul>
              <li v-for="stat in this.collectionStats.rarityCounts" :key="stat.rarity">
                {{ stat.rarity }} : {{ stat.count }}
              </li>
            </ul>
          </div>
          <div class="stat-item">
            <h3>Total Value</h3>
            <p>{{ this.collectionStats.totalCollectionPrice }}</p>
          </div>
        </div>
        <div class="card-container" v-if="this.displayedCards.length > 0">
          <div class="card" v-for="card in this.displayedCards" v-bind:key="card.cardId">
            <router-link class="router-link" v-bind:to="{ name: 'cardDetails', params: { id: card.cardId } }">
              <img :src="card.imageUrl" />
            </router-link>
          </div>
        </div>
      </div>
      <div class="right-container">
        <div class="stat-item">
          <h3>Sets</h3>
          <ul>
            <li v-for="stat in this.collectionStats.setNameCounts" :key="stat.setName">
              {{ stat.setName }}: {{ stat.count }}
            </li>
          </ul>
        </div>
        <div class="collection-stats">Collection Statistics</div>

        <div class="stats-row">
          <div class="stat-item">
            <h3>Card Types</h3>
            <ul>
              <li v-for="stat in this.collectionStats.cardTypeCounts" :key="stat.cardType">
                {{ stat.cardType }} : {{ stat.count }}
              </li>
            </ul>
          </div>

          <div class="stat-item">
            <h3>Colors</h3>
            <ul>
              <li v-for="stat in this.collectionStats.cardColorCounts" :key="stat.cardColor">
                {{ stat.cardColor }}: {{ stat.count }}
              </li>
            </ul>
          </div>

          
        </div>
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
      collectionStats: {
      },
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
    getCollectionStats() {
      CollectionService.getCollectionStats(this.$route.params.id)
        .then((response) => {
          console.log(response.data)
          this.collectionStats = response.data
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
    this.getCollectionStats();
  },
};
</script>

<style scoped>
* {
  color: var(--onyx);
}
.collection-controls {
  display: flex;
  justify-content: space-evenly;
  width:20%;
  margin: auto;
  gap:20px;
  padding:10px;
}
.content-container {
  display: flex;
  width: 100%;
  border: 2px solid green;
}
button{
  padding: 10px;
}
.left-container {
  width: 75%;
  margin: 20px;
  border: 2px solid rgb(255, 0, 221);
  
}
.right-container{
  display: flex;
  flex-direction: column;
  border: 2px solid red;
  align-items: center;
  width:25%;
  margin: 20px;
}
.header-stats {
  width: 100%;
  height: 200px;
  display: flex;
  border: 2px solid blue;
  justify-content: space-evenly;
}
.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: center;
  row-gap: 20px;
  width: 100%;
  
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
.stat-item {
  color: var(--onyx);
}
</style>