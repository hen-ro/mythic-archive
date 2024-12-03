<template>
    <div class="card-details-container">
        <div class="card-details-header">
            <h2> {{ card.name }}</h2>
            <button @click="closeDetails">Close</button>
        </div>

        <div class = "card-details-body">
            <div class="card-details-image">
                <img :src="card.imageUrl" :alt="card.name" />
        </div>

        <div class = "card-details-info">
            <p><strong>Description:</strong> {{ card.description }}</p>
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

    <div class="card-details-actions">
        <button @click="addToCollection">Add to Collection</button>
        <button @click="removeFromCollection">Remove from Collection</button>
    </div>
    </div>
</template>

<script>
export default {
    name: 'CardDetailsView',
    props: {
        card: {
            type: Object,
            required: true
        }
    },
    methods: {
        addToCollection() {
            this.$emit('add-to-collection', this.card);
        },
        removeFromCollection() {
            this.$emit('remove-from-collection', this.card);
        },
        closeDetails() {
            this.$emit('close-details');
        }
    }
}
</script>

<style scoped>
.card-details-container {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 16px;
  max-width: 300px;
  margin: 0 auto;
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
  display: flex;
  gap: 16px;
}

.card-details-image img {
  width: 200px;
  height: auto;
  border-radius: 8px;
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

</style>
  
