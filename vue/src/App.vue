<template>
  <div id="capstone-app">
    <div id="header">
      <router-link class="nav-item" v-bind:to="{ name: 'home' }">
        <div class="logo"><img src='/images/MALogo.png' class="website-logo"></div>
      </router-link>
      <div id="nav">
        <router-link class="nav-item" v-bind:to="{ name: 'home' }">Home<img src='/images/HomeIcon.png'
            class="icon" /></router-link>
        <router-link class="nav-item" v-bind:to="{ name: 'search' }">Search<img src='/images/SearchIcon.png'
            class="icon-search" /></router-link>
        <router-link class="nav-item" v-bind:to="{ name: 'collections' }">Collections<img src='/images/CollectionIcon.png'
            class="icon" /></router-link>
        <router-link class="nav-item" v-bind:to="{ name: 'cardDetails', params: { id: randomCardUrl } }"
          v-if="randomCardUrl"><img src="/images/shuffleIcon.png" class="shuffle-icon" @click="shuffle"/>
        </router-link>
        <router-link class="nav-item" v-bind:to="{ name: 'account' }" v-if="this.$store.state.token != ''">
          <img class="account" src='/images/accountIcon.png' />
        </router-link>
        <router-link class="nav-item" v-bind:to="{ name: 'login' }" v-if="this.$store.state.token === ''">
          Sign In
        </router-link>
      </div>
    </div>
    <aside id="sidebar">
      <a href="https://www.youtube.com" target="_blank"><button type="button" id="sidebar-1"></button></a>
      <a href="https://www.google.com" target="_blank"><button type="button" id="sidebar-2"></button></a>
      <a href="https://www.twitter.com" target="_blank"><button type="button" id="sidebar-3"></button></a>
      <a href="https://www.facebook.com" target="_blank"><button type="button" id="sidebar-4"></button></a>
      <a href="https://www.instagram.com" target="_blank"><button type="button" id="sidebar-5"></button></a>
    </aside>
    <router-view />
  </div>
</template>

<script>
import SearchService from "./services/SearchService";

export default {
  data() {
    return {
      randomCardUrl: null,
    };
  },
  mounted() {

    this.shuffle();
  },
  methods: {
  shuffle() {
    SearchService.getRandomCard()
      .then((response) => {
        console.log("Fetched card:", response);
        this.randomCardUrl = response.data.id; 
        
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
      
  },
},
};
</script>
<style>
.nav-item,
.nav-item:visited,
#header {
  color: #E7E5DF;
  text-decoration: none;
  font-size: 1.4vw;

}

#nav {
  display: flex;
  justify-content: flex-end;
  margin-right: 40px;
  gap: 40px;
  align-items: center;

}

.logo {
  padding-left: 30px;
}

#header {
  display: flex;
  justify-content: space-between;
  background-color: #282c2e;
  border-bottom: 2px solid #E7E5DF;
  padding-top: 10px;
  padding-bottom: 5px;
}

.account {
  width: 1.8vw;
}

.icon {
  width: 1.2vw;
  margin-left: 10px;
}

.icon-search {
  width: 1vw;
  margin-left: 7px;
}

.shuffle-icon {
  height: 1.4vw;
  padding-top: 10%;
}

.website-logo {
  width: 15vw;
}

#sidebar-1:hover,
#sidebar-2:hover,
#sidebar-3:hover,
#sidebar-4:hover,
#sidebar-5:hover {
  opacity: 1;
}

#sidebar-1:active,
#sidebar-2:active,
#sidebar-3:active,
#sidebar-4:active,
#sidebar-5:active {
  width: 45px;
  height: 45px;
}

#sidebar-1,
#sidebar-2,
#sidebar-3,
#sidebar-4,
#sidebar-5 {
  height: 40px;
  width: 40px;
  background-size: cover;
  border: 0px;
  opacity: 0.7;
}

#sidebar-1 {
  background-image: url("Images/sidebar/sidebar-1.png");
}

#sidebar-2 {
  background-image: url("Images/sidebar/sidebar-2.png");
}

#sidebar-3 {
  background-image: url("Images/sidebar/sidebar-3.png");
}

#sidebar-4 {
  background-image: url("Images/sidebar/sidebar-4.png");
}

#sidebar-5 {
  background-image: url("Images/sidebar/sidebar-5.png");
}

#sidebar {
  display: flex;
  flex-direction: column;
  height: 300px;
  position: fixed;
  margin-top: 300px;
}
</style>
