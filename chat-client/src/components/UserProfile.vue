<template>
  <div class="profile">
    <img :src="profileUrl"/>
  </div>
</template>

<script>
  export default{
    props: {
      profileUrl: String
    }
  }
</script>

<style scoped lang="scss">
  .profile {
    img {
      width: 50px;
      height: 50px;
      border-radius: 50%;
    }
  }
</style>
