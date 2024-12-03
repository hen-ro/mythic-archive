<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link class="nav-item" v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'logout' }" v-if="this.$store.state.token != ''">Logout</router-link>
      <router-link class="nav-item" v-bind:to="{ name: 'login' }" v-if="this.$store.state.token === ''">Sign In</router-link>
    </div>
    <router-view />
  </div>
</template>

