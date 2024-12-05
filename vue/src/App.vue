<template>
  <div id="capstone-app">
    <div id="header">
      <div>PokeMans</div>
      <div id="nav">
      <router-link class="nav-item" v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'search' }">Search</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'collections' }">Collections</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'account' }" v-if="this.$store.state.token != ''">
        <img class="account" src='/public/images/accountIcon.png'/>      
        </router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'login' }" v-if="this.$store.state.token === ''">Sign In</router-link>
    </div>
    </div>
    <router-view />
  </div>
</template>
<style>


.nav-item, .nav-item:visited,#header{
    color: #fd975c;
    text-decoration: none;
    font-size: 1.4vw;
    margin-left: 40px;
}
#nav{
    display:flex;
    justify-content: flex-end;
    margin-right: 40px;
    
}
#header{
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
.account {
  width:1.8vw;
  margin-top:3px;
}
</style>
