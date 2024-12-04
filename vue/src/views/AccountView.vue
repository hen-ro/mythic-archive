<template>
    <div class="account-container">
        <router-link class="nav-item" v-bind:to="{ name: 'myCollection' }" v-if="this.$store.state.token != ''">My Collection</router-link>
        <router-link class="nav-item" v-bind:to="{ name: 'logout' }" v-if="this.$store.state.token != ''">Logout</router-link>
    </div>
</template>
  
<script>
export default {
};
</script>
<style>
.account-container{
    margin: 150px auto;
    text-align: center;
    display: flex;
    flex-direction: column;
}    
</style>