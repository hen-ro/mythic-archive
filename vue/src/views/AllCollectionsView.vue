<template>
   <div>
      <h1>Collections</h1>
      <div class="collection-container">
         <div class="collection-object" v-for="n in 25" :key="n">
            <router-link class="router-link" v-bind:to="{ name: 'collection' }">
               <div class="thumbnail"></div>
               <div class="collection-data">
                  <p>Name</p>
                  <p>Username</p>
                  <p>Total Cards</p>
               </div>
            </router-link>
         </div>
      </div>
   </div>
</template>
  
<script>
import CollectionService from '../services/CollectionService';

export default {

   methods: {
      getAllPublicCollections() {
      CollectionService.getAllPublicCollections()
        .then((response) => {
          console.log (response)
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
      },
      },
      mounted(){
         this.getAllPublicCollections();
      }
   };
</script>
  
<style>
.router-link {
   text-decoration: none;
   color: white;
}

.collection-container {
   display: flex;
   margin: auto;
   width: 70%;
   justify-content: space-evenly;
   flex-wrap: wrap;
}

.collection-object {
   width: 18%;
   background-color: #377c79;
   margin-top: 20px;
}

.thumbnail {
   width: 100%;
   height: 200px;
   /* background-color: #8f8787; */
}

.collection-data {
   text-align: center;
   display: flex;
   flex-direction: column;
}
</style>