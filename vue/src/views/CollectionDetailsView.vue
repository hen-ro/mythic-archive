<template>
  <div>
    <h1>{{ collectionName }}</h1>
  </div>
</template>
  
  <script>
export default {
  data() {
    return {
      collectionName: "",
    };
  },
  mounted() {
    this.collectionName = this.$route.params.name;
    console.log(this.collectionName);
  },

  methods: {},
};
</script>
  
  <style scoped>
h1 {
  color: #1e7a76;
}
</style>
