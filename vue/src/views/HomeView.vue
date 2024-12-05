<template>
  <div>
    <h1>Home</h1>
  </div>
  <div class="banner">
    <div class="banner-text"></div>
    <div class="banner-image">
      <div class="card"></div>
    </div>
  </div>
</template>
  
<script>
export default {
};
</script>
<style>
.banner{
  width: 100%;
  height: 500px;
  background-color: #552b36;
  display: flex;
  align-items: center;
  justify-content: center;
}
.banner-text, .banner-image{
  background-color: #683442;
  height: 85%;
  width: 30%;
  margin: auto 20px;
}
</style>