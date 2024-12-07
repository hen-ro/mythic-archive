<template>
  <div>
    <h1>Collections</h1>
    <div class="collection-container">
      <div class="collection-object" v-for="collection in collections" v-bind:key="collection.ownerId">
        <router-link class="router-link" v-bind:to="{ name: 'collectionDetails', params: { name: collection.collectionName } }" >
          <img class="thumbnail" :src="collection.thumbnailUrl">
          <div class="collection-data">
            <p> {{ collection.collectionName }} </p>
            <p> {{ collection.username }} </p>
            <p>{{ collection.cardCount }}</p>
          </div>
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
      collections: [],
    };
  },

  methods: {
    getAllPublicCollections() {
      CollectionService.getAllPublicCollections()
        .then((response) => {
          console.log(response);
          this.collections = response.data.map((collection) => ({
            cardCount: collection.cardCount,
            collectionName: collection.collectionName,
            ownerId: collection.ownerId,
            thumbnailUrl: collection.thumbnailUrl,
            username: collection.username
          }));
          
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
  },
  mounted() {
    this.getAllPublicCollections();
  },
};
</script>

<style>

.collection-container {
  display: flex;
  margin: auto;
  width: 70%;
  justify-content: space-evenly;
  flex-wrap: wrap;
}

.collection-object {
  width: 18%;
  min-width: 200px;
  background-color: var(--perry);
}

.thumbnail {
  width: 100%;
  height: 200px;
}

.collection-data {
  text-align: center;
  display: flex;
  flex-direction: column;
  height: 70px;
  justify-content: space-evenly;
  background-color: var(--perry);
  padding-bottom: 10px;
}
p {
   margin: 0;
}
</style>