<template>
  <div>
    <div class="error-section container m-auto my-20 px-2">
      <div class="flex flex-col items-center gap-2 text-center">
        <span class="text-8xl text-gray-400">404</span>
        <h1 class="text-4xl  text-[#02346f] font-semibold text-primary-color">Запрашиваемая страница не найдена</h1>
        <p class="text-lg text-gray-500">
          Страница, которую вы ищете, не существует. Возможно, она было перемещена или удалена.
        </p>
        <a  @click.stop="$router.push('/')" class="btn-effect btn transition-all-300 bg-[#bb8d54] border-[#bb8d54] hover:bg-[#02346f] hover:border-[#02346f] inline-block rounded-lg py-4 px-5 bg-primary-color   text-white">
          <span>На главную</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
</script>