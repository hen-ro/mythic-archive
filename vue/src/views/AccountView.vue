<template>
    <div class="account-container">
        <router-link class="account-link" v-bind:to="{ name: 'collectionDetails', params: {id: this.collectionId} }" v-if="this.$store.state.token != ''">My Collection</router-link>
        <router-link class="account-link" v-bind:to="{ name: 'logout' }" v-if="this.$store.state.token != ''">Logout</router-link>
    </div>
</template>
  
<script>
import CollectionService from '../services/CollectionService';


export default {

    data() {
        return {
            collectionId: 0
        }
    },

    methods: {
        getCollectionIdByUser() {
            CollectionService.getCollectionIdByUser()
        .then((response) => {
          this.collectionId = response.data;
          console.log(this.collectionId)
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
        }
    },

    mounted() {
        this.getCollectionIdByUser();
    },
};
</script>

<style>
.account-container{
    margin: 150px auto;
    text-align: center;
    display: flex;
    flex-direction: column;
}    

.account-link:visited, .account-link{
    color:var(--onyx);
    text-decoration: none;
    font-size:2rem;
}

</style>