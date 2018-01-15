<template>
  <div class="message-box" :class="selfOpposite">
    <UserProfile :profileUrl="profile"></UserProfile>
    <div class="message-area">
      <span>{{message}}</span>
    </div>
  </div>
</template>

<script>
  import UserProfile from './UserProfile.vue'
  export default{
    props: {
      profile: String,
      message: String,
      selfOpposite: String
    },
    components: {
      UserProfile
    }
  }
</script>

<style scoped lang="scss">
  $selfColor: #F1F1FB;
  $oppisiteColor: #989AD7;
  .message-box {
    display: flex;
    justify-content: space-around;
    width: 40%;
    .message-area{
      border-radius: 15px;
      width: 80%;
    }
  }

  .self {
    float: right;
    .message-area{
      background: $selfColor;
      order: -1;
    }
  }

  .opposite {
    float: left;
    .message-area{
      background:$oppisiteColor;
    }
  }
</style>
