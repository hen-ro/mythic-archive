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
                    <h2>{{ card.name }}</h2>
                </div>
            <div class="stat-container">

                <div class="stat">
                    <h3>Set</h3>
                    <span>{{ card.set_name }}</span>
                </div>
                <div class="stat">
                    <h3>Type</h3>
                    <span>{{ card.type_line }}</span>
                </div>
                <div class="stat">
                    <h3>Oracle Text</h3>
                    <span v-html="formattedOracleText"></span>
                </div>
                <div class="stat">
                    <h3>Flavor Text</h3>
                    <span>{{ card.flavor_text || "No Flavor Text" }}</span>
                </div>
                <div class="stat">
                    <h3>Mana Cost</h3>
                    <div v-html="formattedManaCost"></div>
                </div>
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
import CollectionService from "../services/CollectionService";

export default {
    name: "CardDetailsView",
    data() {
        return {
            card: {},
            cardCount: "test"
        };
    },
    computed: {
        formattedManaCost() {
            if (!this.card.mana_cost) { return ""; }
            return this.card.mana_cost.replace(/{(\w)}/g, (match, symbol) => {
                return `<img src="/Images/MTGMana/{${symbol}}.svg" alt="${symbol}" style="height:30px; margin:0 2px;" />`;
            });
        },
        formattedOracleText() {
            if (!this.card.oracle_text) { return ""; }
            return this.card.oracle_text.replace(/{(\w)}/g, (match, symbol) => {
                
                return `<img src="/Images/MTGMana/{${symbol.replace("/", "")}}.svg" alt="${symbol}" style="height:16px; margin:0 2px;" />`;
            });
        }
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
        CollectionService.getCardsInCollection(this.$route.params.id)
            .then((response) => {
             
                this.cardCount = response.data;
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
    background-color: var();
}

.card-container,
.details-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
    height: 90%;
    margin: 0;
    gap: 20px;
}

.card-container {
    min-width: 400px;
    width: 35%;
    max-width: 700px;
    margin-bottom: 20px;
}

.details-container {
    min-width: 700px;
    width: 45%;
    max-width: 1100px;
    height: auto;
    border-radius: 2px;
    display: flex;
    justify-content: space-between;
    color: var(--platinum);
    background-color: var(--onyx);
    padding: 30px;
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
    opacity: 0.6;
}

.plus-minus:hover {
    opacity: 1;
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
    width: 95%;
    margin-bottom: 10px
}

.legality {
    padding: 6px;
    height: 30px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    font-size: 1rem;

}

.legal {
    background-color: rgb(115, 202, 115);
}

.not-legal {
    background-color: rgb(192, 120, 120);
}

h3 {
    margin: 0;
}

.stat {
    display: inline-flex; 
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
    border-radius: 8px;
    padding: 15px;
    gap: 5px; 
    width: 25%; 
    height: auto; 
    max-width: 400px;
    min-width: 300px;
}

.stat-container {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: space-between; 
    align-items: flex-start;
    height: 60%;
    flex-wrap: wrap;
    max-width: 90%;
    
}

@media (max-width: 768px) {
    .details-container {
        width: 90%;
        min-width: 0;
    }
    .stat-container{
        min-width: 0;
        width:90%;
    }
}
</style>
