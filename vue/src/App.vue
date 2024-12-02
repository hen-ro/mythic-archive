<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="this.$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-if="this.$store.state.token === ''">Sign In</router-link>
    </div>
    <router-view />
  </div>
</template>
