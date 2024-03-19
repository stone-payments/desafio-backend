<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Product>
 */
class ProductFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'title' => $this->faker->sentence(3),
            'price' => $this->faker->numberBetween(100, 1000),
            'zipcode' => $this->faker->postcode(),
            'seller' => $this->faker->name(),
            'thumbnailHd' => $this->faker->imageUrl(640, 480, 'cats', true),
            'date' => $this->faker->date()
        ];
    }
}
