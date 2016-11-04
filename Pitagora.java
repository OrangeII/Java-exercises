/*
this prints the Multiplication Table
or Pythagorean Table
*/
class Pitagora{
  public static void main(String[] args){
    for(int i = 0; i <= 10; i++){
      for(int j = 0; j <= 10; j++){
        System.out.print(i * j + "\t");
      }
      System.out.print("\n");
    }
  }
}
