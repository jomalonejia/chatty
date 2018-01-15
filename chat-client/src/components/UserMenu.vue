<template>
  <div class="user-menu">
    <UserProfile :profileUrl="user.profile"></UserProfile>
    <div class="message">
      <div>
        <div>{{user.username}}</div>
        <div>{{user.latestMessage|messageFilter}}</div>
      </div>
    </div>
    <div class="message-time">
      13m
    </div>
  </div>
</template>
<script>
  import UserProfile from './UserProfile.vue'
  export default {
    name: 'userMenu',
    props: {
      user: Object
    },
    filters: {
      messageFilter: function (message) {
        return message.length > 30 ? `${message.substring(0, 30)}...` : message
      }
    },
    components: {
      UserProfile
    }
  }
</script>
<style scoped lang="scss">
  @import "../assets/styles/common";
  .user-menu{
    display: flex;
    align-items: center;
    .message{
      color: $sidebar-font-color;

    }
    .message-time{

    }
  }
</style>
