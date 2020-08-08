<template>
  <div class="sample">
    <h1>This is an sample page</h1>
    <ul  v-for="article in articles" :key="article.id">
      <li>{{ article.postName }}</li>
      <li>{{ article.postText }}</li> 
      <li>{{ article.postDate }}</li>
      <li><img v-bind:src="'https://sample-vue-test-bucket.s3-ap-northeast-1.amazonaws.com/'+article.postImage" height="30" /></li>
    </ul>
    <a href="/sample/create">追加</a>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'sample',
  data(){
    return {
      articles: [],
    }
  },
  created(){
    let vm = this
    this.$axios.get('http://ec2-18-181-145-245.ap-northeast-1.compute.amazonaws.com/api/list')
    .then((response) => {
      vm.articles = response.data
    })
  }
}
</script>
