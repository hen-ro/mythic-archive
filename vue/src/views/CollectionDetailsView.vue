<template>
  <div class="collections-details">
    <h1 class="collection-name">{{ collection.collectionName }}</h1>
    <div class="collection-controls" v-if="this.$route.params.id == this.$store.state.user.id">
      <button id="btn-show-name-input" @click="showNameInput = true">Rename Collection</button>
      <button id="btn-publish" @click="setPublic" v-if="!collection.isPublic">Publish Collection</button>
      <button id="btn-unpublish" @click="setPrivate" v-if="collection.isPublic">Unpublish Collection</button>
    </div>
    <div class="collection-name-input" v-if="showNameInput === true">
      <button id="btn-close-name-input" @click="showNameInput = false">x</button>
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
          <div class="pie-chart-container">
            <canvas id="pieChart"></canvas>
          </div>
          <div class="stat-item">
            <h3>Total Value</h3>
            <p>{{ this.collectionStats.totalCollectionPrice }}</p>
            <p>There {{ this.collectionStats.cardTypesWithoutPrice == 1 ? 'is' : 'are' }} <strong>{{ this.collectionStats.cardTypesWithoutPrice }}</strong> {{ this.collectionStats.cardTypesWithoutPrice == 1 ? 'type' : 'types' }} of {{ this.collectionStats.cardTypesWithoutPrice == 1 ? 'card' : 'cards' }} in this collection without price data</p>
            <p>({{ this.collectionStats.cardsWithoutPrice }} total {{ this.collectionStats.cardsWithoutPrice == 1 ? 'copy' : 'copies' }})</p>
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
        <div class="collection-stats">Collection Statistics</div>
        <div class="total-cards">
          <h3>Total Cards: {{ this.collection.totalCards }}</h3>
        </div>
        <div class="stat-item">
          <h3>Sets</h3>
          <ul>
            <li v-for="stat in this.collectionStats.setNameCounts" :key="stat.setName">
              {{ stat.setName }}: {{ stat.count }}
            </li>
          </ul>
        </div>
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
import Chart from "chart.js/auto";  // Import Chart.js
import CollectionService from "../services/CollectionService";

export default {
  data() {
    return {
      showNameInput: false,
      displayedCards: [],
      collectionStats: {},
      collection: {
        cardCount: 0,
        collectionName: "",
        ownerId: 0,
        thumbnailUrl: "",
        username: "",
        cards: [],
      },
      chartInstance: null,  // Store the Chart instance to easily update it later
    };
  },

  methods: {
    rename() {
      let collectionName = document.getElementById('txt-nameInput').value;
      CollectionService.renameCollection(this.$route.params.id, collectionName)
        .then((response) => {
          this.collection.collectionName = collectionName;
          this.showNameInput = false;
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    getCollectionStats() {
      CollectionService.getCollectionStats(this.$route.params.id)
        .then((response) => {
          console.log(response.data);
          this.collectionStats = response.data;
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    setPrivate() {
      CollectionService.setCollectionPrivate(this.$route.params.id)
        .then((response) => {
          this.collection.isPublic = false;
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    setPublic() {
      CollectionService.setCollectionPublic(this.$route.params.id)
        .then((response) => {
          this.collection.isPublic = true;
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
      let tempArray = this.collection.cards.filter((card) => {
        return card.cardName.toLowerCase().includes(search.toLowerCase()) ||
          card.cardType.toLowerCase().includes(search.toLowerCase()) ||
          card.rarity.toLowerCase().includes(search.toLowerCase()) ||
          card.setName.toLowerCase().includes(search.toLowerCase());
      });
      this.displayedCards = tempArray;
    },
    createPieChart() {
  if (this.collectionStats && this.collectionStats.rarityCounts && this.collectionStats.rarityCounts.length > 0) {
    const labels = this.collectionStats.rarityCounts.map(stat => stat.rarity);
    const data = this.collectionStats.rarityCounts.map(stat => stat.count);
    const backgroundColors = this.collectionStats.rarityCounts.map(() => '#444444');

    const ctx = document.getElementById('pieChart').getContext('2d');

    // Set the canvas width and height
    const canvas = document.getElementById('pieChart');
    const aspectRatio = canvas.width / canvas.height;  // Maintain aspect ratio
    canvas.width = 400;  // Set width to your preferred size
    canvas.height = canvas.width / aspectRatio;  // Set height based on aspect ratio

    // Ensure the device pixel ratio is applied for higher DPI screens
    const devicePixelRatio = window.devicePixelRatio || 1;
    canvas.style.width = '100%';  // Ensure the canvas is responsive
    canvas.style.height = 'auto';

    // Apply the device pixel ratio for sharpness
    const width = canvas.offsetWidth * devicePixelRatio;
    const height = canvas.offsetHeight * devicePixelRatio;

    canvas.width = width;
    canvas.height = height;

    if (this.chartInstance) {
      this.chartInstance.destroy();
    }

    this.chartInstance = new Chart(ctx, {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [{
          data: data,
          backgroundColor: backgroundColors,
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false, // Disable aspect ratio to adjust canvas size dynamically
        plugins: {
          legend: {
            position: 'left',
            align: 'start',
            labels: {
              boxWidth: 10,
              padding: 10,
            }
          }
        }
      },
    });
  } else {
    const ctx = document.getElementById('pieChart').getContext('2d');
    const canvas = document.getElementById('pieChart');
    const aspectRatio = canvas.width / canvas.height;
    canvas.width = 400;
    canvas.height = canvas.width / aspectRatio;

    const devicePixelRatio = window.devicePixelRatio || 1;
    const width = canvas.offsetWidth * devicePixelRatio;
    const height = canvas.offsetHeight * devicePixelRatio;

    canvas.width = width;
    canvas.height = height;

    if (this.chartInstance) {
      this.chartInstance.destroy();
    }
    this.chartInstance = new Chart(ctx, {
      type: 'pie',
      data: {
        labels: ['No data available'],
        datasets: [{
          data: [100],
          backgroundColor: ['#ddd'],
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'left',
            align: 'start',
            labels: {
              boxWidth: 10,
              padding: 10,
            }
          }
        }
      },
    });
  }
}
  },
  watch: {
    'collectionStats.rarityCounts': function (newVal) {
      if (newVal && newVal.length > 0) {
        this.createPieChart();
      }
    },
  },

  created() {
    this.getCollectionById();
    this.getCollectionStats();
  },

  mounted() {
    if (this.collectionStats.rarityCounts && this.collectionStats.rarityCounts.length > 0) {
      this.createPieChart();
    }
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
  align-items: center;
}
.pie-chart-container {
  width: 200px;
  height: 95%;

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
  justify-content: space-between;
}
input[type="text"] {
  width: 80%;
}
#btn-close-name-input {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
}
#pieChart{
  min-width: 200px!important;
  min-height: 200px!important;
 
}
</style>