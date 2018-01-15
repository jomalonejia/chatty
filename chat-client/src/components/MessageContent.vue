<template>

    <div class="content-area">
      <textarea v-model="content" rows="3"></textarea>
      <button @click="send()">Send</button>
    </div>
    <!--@keydown.enter="send()"-->
</template>
<script>
  export default{
    data () {
      return {
        content: ''
      }
    },
    props: {
      sendMessage: Function
    },
    methods: {
      send () {
        if (this.content === '') return
        this.sendMessage(this.content)
        this.content = ''
      }
    }
  }
</script>
<style scoped lang="scss">

  .content-area{
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  textarea {
    color: #666;
    font-size: 14px;
    margin: 5px 0px 10px 0px;
    padding: 10px;
    height: 75px;
    width: 350px;
    border:#35a5e5 1px solid;
    font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif;
    transition: all 0.25s ease-in-out;
    box-shadow: 0 0 5px rgba(81, 203, 238, 0);
    border-radius: 10px;
    &:focus{
      color:#000;
      outline:none;
      border:#35a5e5 1px solid;
      box-shadow: 0 0 5px rgba(81, 203, 238, 1);
    }
  }
  button{
    width: 100px;
    height: 60px;
    background: #76B3FA;
    border-radius: 100px;
    border:#35a5e5 1px solid;
    color: #fff;
    text-decoration: none;
    font-size: 1.45em;
    &:hover{
      cursor: pointer;
      border:#35a5e5 2px solid;
    }
    &:active, &:focus{
      outline: none;
    }
  }
</style>
