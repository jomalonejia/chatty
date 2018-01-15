<template>
  <div class="container">
    <div class="window">
      <div class="bar side-bar">
        <div class="side-header">
          <span><icon name="angle-left" :scale="2"></icon></span>
          <span class="logo">chatty!</span>
          <span><icon name="align-justify" :scale="2"></icon></span>
        </div>
        <div class="side-user">
          <ul class="side-user-menu">
            <li class="user-menu-item" v-for="(user, index) in users">
              <UserMenu :user="user"></UserMenu>
            </li>
          </ul>
        </div>
      </div>
      <div class="bar chat-bar">
        <div class="info-bar">
          <span><icon name="commenting" :scale="2"></icon></span>
          <span><icon name="user" :scale="2"></icon></span>
        </div>
        <div class="message-bar">
          <div class="message-container"
               v-for="(message, index) in messages">
            <MessageBox
              :profile="users[0].profile"
              :message="message.message"
              :selfOpposite="message.channel == 'self' ? 'self' : 'opposite'"
            ></MessageBox>
          </div>
        </div>
        <div class="content-bar">
          <MessageContent
            :content="tempContent"
            :sendMessage="sendMessage"></MessageContent>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import UserMenu from '../components/UserMenu.vue'
  import MessageBox from '../components/MessageBox.vue'
  import MessageContent from '../components/MessageContent.vue'
  import {MessageState} from '../common/MessageState'
  export default {
    data () {
      return {
        users: [
          {
            username: 'user1',
            profile: 'static/profiles/1.jpg',
            latestMessage: 'The public meeting in February 1919 agreed to appoint Sir Edwin Lutyens as architect for the project.'
          },
          {
            username: 'user2',
            profile: 'static/profiles/2.jpg',
            latestMessage: 'In the aftermath of the First World War and its unprecedented casualties'
          },
          {
            username: 'user3',
            profile: 'static/profiles/3.jpg',
            latestMessage: 'thousands of war memorials were built across Britain'
          },
          {
            username: 'user4',
            profile: 'static/profiles/4.jpg',
            latestMessage: 'Almost all towns and cities erected some form of memorial to commemorate their fallen'
          },
          {
            username: 'user5',
            profile: 'static/profiles/5.jpg',
            latestMessage: 'The mayor of Rochdale called a public meeting on 10 February 1919'
          },
          {
            username: 'user6',
            profile: 'static/profiles/6.jpg',
            latestMessage: 'three months after the armistice'
          },
          {
            username: 'user7',
            profile: 'static/profiles/7.jpg',
            latestMessage: 'to discuss proposals for the town\'s commemorations'
          },
          {
            username: 'user8',
            profile: 'static/profiles/8.jpg',
            latestMessage: 'Consensus was that the town should have a physical monument and a fund to provide for wounded servicemen'
          }
        ],
        messages: [
          {
            channel: 'self',
            message: 'nihaoa'
          },
          {
            channel: 'opposite',
            message: 'it\'s so cool'
          },
          {
            channel: 'self',
            message: 'nihaoa'
          }
        ],
        socket: null,
        tempContent: ''
      }
    },
    methods: {
      sendMessage (message) {
        if (this.socket.readyState === WebSocket.OPEN) {
          const obj = {
            type: MessageState.MESSAGE,
            username: '小强',
            message: message
          }
          this.socket.send(JSON.stringify(obj))
          this.tempContent = ''
        } else {
          alert('websocket is not connected!')
        }
      },
      addMessage (message) {
        this.messages.push({
          channel: 'self',
          message: message
        })
      }
    },
    mounted: function () {
      if (window.WebSocket) {
        this.socket = new WebSocket('ws://localhost:8080/websocket')
        this.socket.onmessage = function (event) {
          console.log('.....' + event.data)
          this.addMessage(event.data)
        }.bind(this)
        this.socket.onopen = function (event) {
          console.log('success')
        }
        this.socket.onclose = function (event) {
          console.log('failed')
        }
      } else {
        alert('not support websocket')
      }
    },
    components: {
      UserMenu,
      MessageBox,
      MessageContent
    }
  }
</script>

<style lang="scss">
  @import "../assets/styles/common";

  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    .window {
      width: 75%;
      height: 600px;
      display: flex;
      .bar {

      }
      .side-bar {
        background: #3B3E99;
        overflow: scroll;
        width: 45%;
        border: {
          top-left-radius: $radius-size;
          bottom-left-radius: $radius-size;
        }
      }
      .chat-bar {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        //background: #E4E7ED;
        width: 55%;
        border: {
          top-right-radius: $radius-size;
          bottom-right-radius: $radius-size;
        }
      }
    }
  }

  .side-header {
    display: flex;
    justify-content: space-between;
    span {
      padding: 30px;
      .fa-icon {
        color: $sidebar-font-color;
        cursor: pointer;
      }
    }
    .logo {
      color: $sidebar-font-color;
    }
  }

  .side-user {
    .side-user-menu {
      display: flex;
      flex-direction: column;
      .user-menu-item {
        min-height: 100px;
      }
    }
  }

  .info-bar {
    display: flex;
    justify-content: space-between;
    span {
      padding: 30px;
      .fa-icon {
        color: #D7D8F1;
        cursor: pointer;
      }
    }
  }

  .message-bar {
    display: flex;
    flex-direction: column;
    .message-container {
      padding: {
        left: 10%;
        right: 10%;
      }
    ;
    }
  }

  .content-bar {
    padding-bottom: 50px;
  }
</style>
