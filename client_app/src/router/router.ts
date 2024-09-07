import Main from "../pages/Main.vue";
import Login from "../pages/Login.vue";
import Products from "../pages/Products.vue";
import Categories from "../pages/Categories.vue";
import Category from "../pages/Category.vue";
import Product from "../pages/Product.vue";
import SearchPage from "../pages/SearchPage.vue";
import Basket from "../pages/Basket.vue";
import Favorite from "../pages/Favorite.vue";
import NotFound from "../pages/NotFound.vue";
import Questions from "../pages/Questions.vue";
import Contacts from "../pages/Contacts.vue";
import Orders from '../pages/Orders.vue'

import { useUserStore } from "../stores/userStore";
import { createRouter, createWebHistory } from "vue-router";
import UserPage from "../pages/UserPage.vue";

const routes = [
  {
    path: "/",
    component: Main,
  },
  {
    path: "/favorite",
    component: Favorite,
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/product",
    component: Products,
  },
  {
    path: "/basket",
    component: Basket,
  },
  {
    path: "/product/:productName",
    component: Product,
  },
  {
    path: "/category",
    component: Categories,
  },
  {
    path: "/order",
    component: Orders,
  },
  {
    path: "/category/:categoryName",
    component: Category,
    props: true,
  },
  {
    path: "/category/:categoryName/:subcatery",
    component: Category,
    props: true,
  },
  {
    path: "/search",
    component: SearchPage,
  },
  {
    path: "/questions",
    component: Questions,
  },
  {
    path: "/contacts",
    component: Contacts,
  },
  {
    path: "/:pathMatch(.*)*",
    component: NotFound,
  },
  {
    path: "/user",
    component: UserPage,
  },
];

const router = createRouter({
  routes,
  history: createWebHistory(import.meta.env.BASE_URL),
});
router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  if ((to.fullPath === '/favorite' || to.fullPath === '/basket' || to.fullPath === '/user') && userStore.user.name.length === 0) {
    next("/login");
  }
  next();
})
export default router;
