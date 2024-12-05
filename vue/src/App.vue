<template>
  <div id="capstone-app">
    <div id="header">
      <div class="logo">PokeMans</div>
      <div id="nav">
      <router-link class="nav-item" v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'search' }">Search</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'collections' }">Collections</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'account' }" v-if="this.$store.state.token != ''">
        <img class="account" src='/images/accountIcon.png'/>      
        </router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'login' }" v-if="this.$store.state.token === ''">Sign In</router-link>
    </div>
    </div>
    <router-view />
  </div>
</template>
<style>


.nav-item, .nav-item:visited,#header{
    color: #E7E5DF;
    text-decoration: none;
    font-size: 1.4vw;
  
}
#nav{
    display:flex;
    justify-content: flex-end;
    margin-right: 40px;
    gap: 40px;
 
}
.logo{
  padding-left: 30px ;
}
#header{
  display: flex;
  justify-content: space-between;
  background-color: #393E41;
  border-bottom:2px solid #E7E5DF;
  padding-top: 10px;
  padding-bottom: 5px;
}
.account {
  width:1.8vw;
}

</style>
