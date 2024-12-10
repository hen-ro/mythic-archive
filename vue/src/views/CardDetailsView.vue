<template>
    <div class="card-details-container">
        <div class="card-details-header">
            <h2>{{ card.name }}</h2>
            <button @click="closeDetails">Close</button>
        </div>
        <div class="card-details-body">
            <div class="card-details-image">
                <img :src="card.image_uris ? card.image_uris?.large : card.card_faces ? card.card_faces[0].image_uris?.large : ''"
                    :alt="card.name" />
            </div>

            <div class="card-details-info">
                <p><strong><u>Type:</u></strong> {{ card.type_line }}</p>
                <p><strong>Mana Cost:</strong> <span class="value-text">{{ card.mana_cost }}</span></p>
                <p><strong>Color Identity:</strong> {{ card.color_identity }}</p>
                <p><strong>Set:</strong> {{ card.set_name }}</p>
                <p><strong>Description:</strong> {{ card.oracle_text }}</p>
                <p><strong>Rarity:</strong> {{ card.rarity }}</p>
                <div v-if="card.stats">
                    <p><strong>Stats:</strong></p>
                    <ul>
                        <li v-for="(value, stat) in card.stats" :key="stat">
                            {{ stat }}: {{ value }}
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <button @click="this.$store.dispatch('addToCollection', { card: this.card, quantity: 1 })"> Add to Collection </button>
        <button @click="this.$store.dispatch('removeFromCollection', { card: this.card, quantity: 1 })"> Remove from Collection </button>
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
        });
    }
};
</script>

<style scoped>
.card-details-container {
    border-radius: 8px;
    padding: 16px;
    width: 500px;
    margin: 20px auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: #252525;
    border: 5px solid var(--perry);
}

.card-details-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;

}

.card-details-header h2 {
    margin: 0;

}

.card-details-body {
    gap: 16px;
}

.card-details-image {
    display: flex;
    background-color: #424242;
    border-radius: 5px;
}

.card-details-image img {
    width: 70%;
    height: auto;
    margin: 20px auto;
    border-radius: 15px;

}

.card-details-info {
    flex: 1;
}

.card-details-info p {
    margin: 8px 0;
}

.card-details-info ul {
    list-style: none;
    padding: 0;
}

.card-details-info li {
    margin: 4px 0;
}

.card-details-actions {
    margin-top: 16px;
    text-align: right;
    display: flex;
    justify-content: center;

}

.card-details-actions button {
    margin-left: 8px;
    padding: 8px 16px;
    border: none;
    background-color: #007bff;
    color: white;
    cursor: pointer;
    border-radius: 4px;
}

.card-details-actions button:hover {
    background-color: #0056b3;
}

.value-text {
    color: #969696;
}
</style>