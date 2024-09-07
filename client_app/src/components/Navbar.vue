
<template>
  <div class="hidden bg-white md:block">
    <nav class="container px-5 max-w-screen-lg mx-auto">
      <ul class="flex flex-wrap items-center justify-between py-[10px] text-lg">
        <li
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/') }"
        >
          <a @click.stop="$router.push(`/`)">Главная</a>
        </li>

        <li class="underlined-animated group relative">
          <a
            class="btn-open px-2 hover:text-[#bb8d54] flex items-center gap-1 "
          >
            <span>Товары</span>
            <img class="w-3 ml-1" src="/public/images/logo/down-blue.png" />
          </a>
          <div
            class="submenu shadow-xl transition-all-300 z-[1001] invisible absolute left-0 pt-[10px] opacity-0 group-hover:visible group-hover:opacity-100"
          >
            <ul class="relative w-[250px] bg-white text-base shadow-custom-1">
              <li
                class="group-1"
                v-for="category in categories"
                :key="category.id"
              >
                <a
                  @click.stop="$router.push(`/category/${category.titleUrl}`)"
                  class="flex hover:text-[#bb8d54] cursor-pointer items-center gap-2 p-2"
                >
                  <span class="group-1-hover-font">{{ category.title }}</span>
                </a>
              </li>
            </ul>
          </div>
        </li>
        <li
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/order') }"
        >
          <a @click.stop="$router.push(`/order`)">Заказы</a>
        </li>
        <li
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/questions') }"
        >
          <a @click.stop="$router.push(`/questions`)">Вопросы</a>
        </li>
        <li
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/contacts') }"
        >
          <a @click.stop="$router.push(`/contacts`)">Контакты</a>
        </li>
        <li
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/contacts') }"
        >
          <a @click.stop="$router.push(`/user`)">О себе</a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { getСategory, Category } from "../hooks/useCategory";
import { useRoute } from "vue-router"; 

const router = useRoute();
let categories = ref<Category[]>([]);

const isActive = (path: any) => {
  return router.path === path;
};

onMounted(async () => {
  const response = await getСategory();
  categories.value = { ...response };
});
</script>