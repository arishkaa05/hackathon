<template>
  <div
    class="grid md:grid-cols-2 grid-cols-1 gap-4 xl:grid-cols-3"
  > 
    <div
      v-for="block in info"
      :key="block"
      class="transition-all-300 flex w-full items-center gap-4 rounded-lg bg-white p-5 hover:shadow-lg xs:pl-[20%] sm:pl-5"
    >
      <div class="rounded-full border-2 p-3">
        <img class="w-9" :src="'/public/images/logo/' + block.img" />
      </div>
      <div>
        <h6 class="font-bold capitalize">{{ block.title }}</h6>
        <p class="break-all text-sm text-gray-400">
          {{ block.description }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang='ts'>
const props = defineProps({
  info: {
    type: Object,
  },
});
</script>

<style scoped>
</style>