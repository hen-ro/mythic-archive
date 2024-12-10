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
          v-model="searchTerm"
        />
        <button @click="search">
          Search<img src="/images/SearchIconBlack.png" class="search-icon" />
        </button>
      </div>
    </div>
    <div class="advanced-card-container" v-if="collection.cards.length > 0">
      <div
        class="card"
        v-for="(card, index) in cards"
        :key="collection.cards[index].cardId"
        @mouseover="moveCards(index, 250)"
        :style="{ 
          top: card.top + 'px', 
          backgroundImage: `url(${collection.cards[index].imageUrl})` 
        }"
      ></div>
    </div>
    <div v-else>No cards available.</div>
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService";

export default {
  data() {
    return {
      searchTerm: "",
      collection: {
        cards: [],
      },
      cards: [], 
      hoveredIndex: 0, 
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
    getCardsInCollection() {
      CollectionService.getCardsInCollection(this.$route.params.id)
        .then((response) => {
          this.collection.cards = response.data;

          this.cards = response.data.map((_, index) => ({
            top: index * 30,
          }));
        })
        .catch((error) => {
          console.error("Error fetching cards:", error);
        });
    },
    moveCards(hoveredIndex, offset) {
      this.hoveredIndex = hoveredIndex;


      this.cards = this.cards.map((card, index) => {
        if (index < hoveredIndex) {
          return { ...card, top: index * 30 };
        } else if (index === hoveredIndex) {
          return { ...card, top: hoveredIndex * 30 };
        } else {
          return { ...card, top: index * 30 + offset };
        }
      });
    },
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

.card {
  height: 320px;
  width: 220px;
  background-color: var(--perry);
  border-radius: 5px;
  position: absolute;
  transition: top 0.3s ease;
  background-size: cover;
  background-position: center;
}
</style>