import Vue from 'vue'
import Router from 'vue-router'
import Page from '@/pages/Page'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Page',
      component: Page
    }
  ]
})
