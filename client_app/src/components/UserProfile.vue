<template>
  <div class="card h-full w-full bg-base-100 shadow-xl">
    <div class="card-body">
      <div class="flex mb-6 justify-center" v-if="active === 'user' ">
        <div class="w-28 h-28 mb-6  rounded-full border-2 mr-2 p-2">
          <img
            class="w-24 border-full"
            :src="'/public/images/user/' + userStore.user.img + '.png'"
          />
        </div>
      </div>
      <h2 class="block text-2xl font-bold">{{userStore.user.name}}</h2>
      <hr>
      <button class="w-full text-start text-md py-2 px-3 border-y  hover:bg-gray-100 rounded-none" :class="{ 'bg-gray-100 font-semibold': active === 'user' }">Личный кабинет</button>
      <button @click="logout()" class="w-full text-start -mt-2 text-md py-2 px-3 border-y  hover:bg-gray-100 rounded-none">Выйти</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useUserStore } from "../stores/userStore.ts";

const userStore = useUserStore();
const active = ref('user');

const logout = () => {
  userStore.deleteUserData();
  localStorage.removeItem('jwt');
  localStorage.removeItem('user');
}

</script>