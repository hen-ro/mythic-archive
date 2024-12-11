<template>
    <div class="page-container">>

        <div class="card-container">
            <img :src="card.image_uris ? card.image_uris?.large : card.card_faces ? card.card_faces[0].image_uris?.large : ''"
                :alt="card.name" />
            <div class="card-footer">
                <button @click="this.$store.dispatch('removeFromCollection', { card: this.card, quantity: 1 })"
                    class="plus-minus" title="Remove from collection">-</button>
                <div class="card-price">${{ card.prices.usd || '---' }}</div>
                <button @click="this.$store.dispatch('addToCollection', { card: this.card, quantity: 1 })"
                    class="plus-minus" title="Add to collection">+</button>
            </div>
        </div>


        <div class="details-container">
            <div class="details-header">
                <h1>{{ card.name }}</h1>
                <!-- <button @click="closeDetails">Close</button> -->
            </div>
            <div class="format-container">
                <div v-for="(status, format) in card.legalities" :key="format"
                    :class="{ 'legal legality': status === 'legal', 'not-legal legality': status === 'not_legal' }">
                    {{ format }}
                </div>
            </div>
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
.page-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    height: 100%;
    margin-top: 50px;
}

.card-container,
.details-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 90%;
    margin: 0;
}

.card-container {
    min-width: 400px;
    width: 35%;
    max-width: 700px;
}

.details-container {
    min-width: 700px;
    width: 60%;
    max-width: 1100px;
    border: 2px solid var(--onyx);
    height: 700px;
    border-radius: 10px;
    display: flex;
    justify-content: space-between;
    color: var(--onyx);
}

.card-container img {
    width: 70%;
    height: auto;
    border-radius: 15px;
    margin: 0;
}

.card-footer {
    display: flex;
    align-items: center;
}

.plus-minus {
    height: 40px;
    width: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 2rem;
    cursor: pointer;
    border-radius: 5px;
    background-color: var(--perry);
    color: var(--platinum);
}

.card-price {
    font-size: 1.8rem;
    color: var(--onyx);
    margin: 0 30px;
}

.details-header {
    width: 90%;

}

.format-container {
    height: auto; 
    display: flex;
    flex-wrap: wrap;
    gap: 5px; 
    justify-content: flex-start;
    align-items: flex-start; 
    width:95%;
    margin-bottom: 10px
}

.legality {
    padding: 10px;
    height:30px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    
}

.legal {
    background-color: rgb(115, 202, 115);
}

.not-legal {
    background-color: rgb(192, 120, 120);
}
</style>