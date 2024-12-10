<template>
    <div class="page-container">>
        <div class="card-container">
            <div class="card-details-image">
                <img :src="card.image_uris ? card.image_uris?.large : card.card_faces ? card.card_faces[0].image_uris?.large : ''"
                    :alt="card.name" />
            </div>
        </div>

        <div class="details-container">
            <button @click="closeDetails">Close</button>
            <button @click="this.$store.dispatch('addToCollection', { card: this.card, quantity: 1 })"> Add to Collection
            </button>
            <button @click="this.$store.dispatch('removeFromCollection', { card: this.card, quantity: 1 })"> Remove from
                Collection </button>
        </div>
    </div>
</template>

<script>
import SearchService from "../services/SearchService";


export default {
    name: "CardDetailsView",
    data() {
        return {
            card: {},
        };
    },
    methods: {
        closeDetails() {
            this.$emit("close-details");
            this.$router.go(-1);
        }
    },
    created() {
        SearchService.searchById(this.$route.params.id).then((response) => {
            this.card = response.data;
            console.log(response.data)
        });
    }
};
</script>

<style scoped>
.card-container {
    border-radius: 8px;
    width: 200px;
    display: flex;
    flex-direction: column;
    justify-content: center;
}


.card-details-image {
    display: flex;
    border-radius: 5px;
}

.card-details-image img {
    width: 70%;
    height: auto;
    border-radius: 15px;
}

.page-container {
    width: 85%;
    height: 800px;
    display: flex;
    align-items: center;
    margin: auto;
    justify-content: center;
}

.card-container {
    width: 45%;
    height: 90%;
}

.details-container {
    width: 45%;
    height: 90%;
    background-color: #6a7175;
}</style>