<template>
  <div class="banner">
    <div class="banner-text">
      <div>Collect PokeMans, Live Better, Walmart</div>
      <div class="banner-buttons">
        <router-link class="nav-item banner-link" v-bind:to="{ name: 'search' }">Search Cards</router-link>
        <router-link class="nav-item banner-link" v-bind:to="{ name: 'collections' }">Collections</router-link>
        <router-link class="nav-item banner-link" v-bind:to="{ name: 'login' }" v-if="this.$store.state.token === ''">Sign
          In</router-link>
      </div>
    </div>
    <div class="banner-image">
      <div
        class="homepage-card"
        v-for="(card, index) in cards"
        :key="index"
        :style="{ animationDelay: `${index * 0.1}s` }"
        @animationiteration="resetCard(card)"
      >
        <div class="card-front">
          <div class="card-image"></div>
          <div class="card-lines" v-for="n in 5" :key="n"></div>
        </div>
        <div
          class="card-back"
          :style="{
            backgroundImage: card.isAltImage
              ? `url(${card.altImageUrl})`
              : `url('/images/CardBack.jpg')`,
          }"
        ></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cards: Array.from({ length: 20 }, () => ({
        isAltImage: false, 
        altImageUrl: '/images/amongUS.png', 
      })),
    };
  },
  methods: {
    resetCard(card) {
      card.isAltImage = Math.random() < 0.03; 
    },
  },
};
</script>

<style>
.banner {
  width: 100%;
  height: 550px;
  background-color: #393E41;
  display: flex;
  align-items: center;
  justify-content: center;
}

.banner-text,
.banner-image {
  height: 85%;
  width: 30%;
  margin: auto 20px;
}

.banner-image {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  overflow: hidden;
}

.banner-text {
  text-align: center;
  font-size: 3rem;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  gap: 100px;
}

.banner-buttons {
  display: flex;
  gap: 20px
}

.banner-link {
  background-color: #E7BB41;
  border-radius: 5px;
  text-align: center;
  padding: 15px;
  font-size: 1rem;
  white-space: nowrap;
}

.banner-link:nth-child(2) {
  background-color: #E7E5DF;
  color: #393E41;
}

.banner-link:hover {
  background-color: #927830;
}

.banner-link:nth-child(2):hover {
  background-color: #94928e;
}

.homepage-card {
  background-color: #1e7a76;
  height: 24%;
  width: 80px;
  margin: auto 10px;
  border-radius: 5px;
  animation: rotateY 4s linear infinite;
  transform-style: preserve-3d;
  position: relative;
  perspective: 1000px;
}

.card-front,
.card-back {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  border-radius: 5px;
}

.card-front {
  background-color: #24a7a1;
}

.card-back {
  background-image: url('/images/CardBack.jpg');
  background-color: #1e8a84;
  background-size: cover;
  transform: rotateY(180deg);
}

.card-image {
  background-color: #1e7a76;
  width: 80%;
  height: 40%;
  margin: auto;
  margin-top: 10px;
  border-radius: 3px;
  margin-bottom: 12px;
}

.card-text {
  display: flex;
  height: 30%;
  width: 80%;
  margin: auto;
  justify-content: space-evenly;

}
.card-line-container{
  display: flex;
  flex-direction: column;
  height:100%;
  width: 100%;
  justify-content: space-evenly;
}
.card-lines {
  width: 80%;
  height: 3%;
  background-color: #1e7a76;
  margin: 4px auto;
}
.emblems{
  height:100%;
  width:10%;
  background-color: red;
}
.homepage-card:hover {
  animation: rotateX 3s linear infinite;
  transform-style: preserve-3d;
}

.content-1-container {
  display: flex;
  justify-content: center;
  height: 500px;
  margin-top: 100px;
  align-items: center;
  gap: 40px;
}

.content-text {
  color: #1e7a76;
  font-size: 1.4rem;
  width: 30%;
  height: 40%;


}

.content-image {
  width: 35%;
  background-color: #1e7a76;
  height: 80%;
  border-radius: 5px;
}

.card-back {
  background-size: cover;
  background-repeat: no-repeat;
  transform: rotateY(180deg);
}
@media(max-width:1000px) {
  .banner-image {
    display: none;
  }

}

@keyframes rotateY {
  0% {
    transform: rotateY(0deg);
  }

  100% {
    transform: rotateY(360deg);
  }
}
</style>